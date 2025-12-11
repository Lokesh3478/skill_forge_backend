package org.msa.skillforge_backend.course.exception;

public class InvalidCourseDetails extends RuntimeException {
    public InvalidCourseDetails(String message) {
        super(message);
    }
}
