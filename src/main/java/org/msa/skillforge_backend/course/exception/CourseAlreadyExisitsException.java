package org.msa.skillforge_backend.course.exception;

public class CourseAlreadyExisitsException extends RuntimeException {
    public CourseAlreadyExisitsException(String message) {
        super(message);
    }
}
