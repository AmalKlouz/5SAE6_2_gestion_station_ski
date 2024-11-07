import { Component } from '@angular/core';
import { CourseComponent } from "./components/course/course.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CourseComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
