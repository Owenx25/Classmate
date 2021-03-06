package com.mobileapp.classmate.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.mobileapp.classmate.ClassmateRepository;
import com.mobileapp.classmate.db.entity.Assignment;
import com.mobileapp.classmate.db.entity.Course;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private ClassmateRepository repository;
    private LiveData<List<Assignment>> allAssignments;
    private LiveData<List<Course>> allCourses;

    public MainViewModel(Application application) {
        super(application);
        repository = new ClassmateRepository(application);
        allAssignments = repository.getAllAssignments();
        allCourses = repository.getAllCourses();
    }

    public LiveData<List<Assignment>> getAllAssignments() {
        return allAssignments;
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public LiveData<Course> getCourse(String name) { return repository.getCourse(name); }

    public LiveData<Assignment> getAssignment(String courseName, String name) {
        return repository.getAssignment(courseName, name);
    }

    public LiveData<List<Assignment>> getCourseAssignments(String course) {
        return repository.getCourseAssignments(course);
    }

    public void updateAssignment(Assignment assignment) {
        repository.updateAssignment(assignment);
    }

    public void updateCourse(Course course) {
        repository.updateCourse(course);
    }

    public void updateAssignCourseName(String oldName, String newName){
        repository.updateCourseName(oldName, newName);
    }

    public void insertCourse(Course course){
        repository.insertCourse(course);
    }

    public void deleteCourse(String courseName) {
        repository.deleteCourse(courseName);
    }

    public void insertAssignment(Assignment assignment) {
        repository.insertAssignment(assignment);
    }

    public void deleteAssignment(String courseName, String name) {
        repository.deleteAssignment(courseName, name);
    }
}
