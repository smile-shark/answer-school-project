package com.smileShark.main.business.logic;

import com.smileShark.main.mapper.SelectId;
import com.smileShark.main.entity.Chapter;
import com.smileShark.main.resoult.Course;
import com.smileShark.main.resoult.Subsection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdList {

    @Autowired
    private SelectId selectCourse;

    public Course[] getAllCourses() {
        List<Course> courses = selectCourse.selectAllCourse();
        Course[] course=new Course[courses.size()];
        courses.forEach(f -> course[courses.indexOf(f)]=f);
        return course;
    }
    public Chapter[] getAllChapters(String courseId) {
        List<Chapter> chapters = selectCourse.selectAllChapter(courseId);
        Chapter[] chapter=new Chapter[chapters.size()];
        chapters.forEach(f -> chapter[chapters.indexOf(f)]=f);
        return chapter;
    }
    public Subsection[] getAllSubsections(String chapterId) {
        List<Subsection> subsections = selectCourse.selectAllSubsection(chapterId);
        Subsection[] subsection = new Subsection[subsections.size()];
        subsections.forEach(f -> subsection[subsections.indexOf(f)] = f);
        return subsection;
    }
}
