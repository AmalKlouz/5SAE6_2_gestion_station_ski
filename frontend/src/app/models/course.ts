export interface Course {
    numCourse?: number;
    level: number;
    typeCourse: 'COLLECTIVE_CHILDREN' | 'COLLECTIVE_ADULT' | 'INDIVIDUAL';
    support: 'SKI' | 'SNOWBOARD';
    price: number;
    timeSlot: number;
  }