package com.mobileapp.classmate.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mobileapp.classmate.db.entity.Assignment;

import java.util.List;

@Dao
public interface AssignmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Assignment assignment);

    @Insert
    void insertAssignments(Assignment... assignments);

    @Update
    void updateAssignments(Assignment... assignments);

    @Update
    void updateAssignement(Assignment assignment);

    @Query("DELETE FROM Assignments WHERE className = :courseName AND name = :assignName")
    void delete(String courseName, String assignName);

    @Query("DELETE FROM Assignments")
    void deleteAll();

    @Query("SELECT * FROM Assignments ORDER BY className ASC")
    LiveData<List<Assignment>> loadAllAssignments();

    // className needs to be wrapped with %(Percent signs)!!!
    @Query("SELECT * FROM Assignments WHERE className LIKE :className")
    List<Assignment> getClassAssignments(String className);

    @Query("SELECT * FROM Assignments WHERE isComplete = 0")
    List<Assignment> getIncompleteAssignments();

    @Query("SELECT * FROM Assignments WHERE isComplete = 1")
    List<Assignment> getCompletedAssignments();

    @Query("SELECT * FROM Assignments WHERE priority = :priority")
    List<Assignment> getPriortyAssignments(int priority);

    // className needs to be wrapped with %(Percent signs)!!!
    @Query("DELETE FROM Assignments WHERE className LIKE :className")
    void deleteClassAssignments(String className);

    @Query("UPDATE Assignments SET priority = :priority WHERE id = :id")
    void updateAssignmentPriority(int id, int priority);

    @Query("SELECT * FROM Assignments WHERE dueDate <= datetime('now','now','+1 day')")
    List<Assignment> getDueTommorow();
}