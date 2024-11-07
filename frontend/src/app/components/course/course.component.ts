import { Component, OnInit } from '@angular/core';
import { Course } from '../../models/course';
import { CourseService } from '../../services/course.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  standalone: true,
  imports: [CommonModule, FormsModule],
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {
  courses: Course[] = [];
  newCourse: Course = {
    level: 1,
    typeCourse: 'INDIVIDUAL',
    support: 'SKI',
    price: 0,
    timeSlot: 1
  };
  
  courseTypes = ['COLLECTIVE_CHILDREN', 'COLLECTIVE_ADULT', 'INDIVIDUAL'];
  supportTypes = ['SKI', 'SNOWBOARD'];

  constructor(private courseService: CourseService) { }

  ngOnInit(): void {
    this.loadCourses();
  }

  loadCourses(): void {
    this.courseService.getAllCourses()
      .subscribe(data => {
        this.courses = data;
      });
  }

  onSubmit(): void {
    this.courseService.addCourse(this.newCourse)
      .subscribe(() => {
        this.loadCourses();
        this.resetForm();
      });
  }

  updateCourse(course: Course): void {
    this.courseService.updateCourse(course)
      .subscribe(() => {
        this.loadCourses();
      });
  }

  resetForm(): void {
    this.newCourse = {
      level: 1,
      typeCourse: 'INDIVIDUAL',
      support: 'SKI',
      price: 0,
      timeSlot: 1
    };
  }
}