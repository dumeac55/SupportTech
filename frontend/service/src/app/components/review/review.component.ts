import { Component } from '@angular/core';
import { TechnicianDto } from '../../model/technician-dto';
import { ReviewDto } from '../../model/review-dto';
import { TechnicianService } from '../../service/technician.service';
import { ReviewService } from '../../service/review.service';
import { UserProfileService } from '../../service/user-profile.service';
import { NotificationService } from '../../service/notification.service';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrl: './review.component.css',
})

export class ReviewComponent {
  technicians: TechnicianDto[] = [];
  selectedTechnicianReviews: ReviewDto[] = [];
  addReview: ReviewDto ={ grade: 0, description:""};
  selectedTechnician: TechnicianDto | null = null;
  panelOpenState = false;
  id?: number;
  grades: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  constructor(
    private technicianService: TechnicianService,
    private reviewServide: ReviewService,
    private userProfile: UserProfileService,
    private notification: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadTechnicians();
  }

  loadTechnicians(): void {
    this.technicianService.getTechnicians().subscribe(
      (data) => (this.technicians = data),
      (error) => console.error(error)
    );
  }

  async selectTechnician(technician: TechnicianDto) {
    this.selectedTechnician = technician;
    if (technician.username) {
      this.id = await this.technicianService.getTechnicianProfileByUsername(
        technician.username
      );
      if(this.id)
      this.reviewServide.getReviewByIdTechnician(this.id).subscribe(
        (data) => (this.selectedTechnicianReviews = data),
        (error) => console.error(error)
      );
    }
  }

  async setReview(description: String | undefined, grade: number | undefined, username: string| undefined){
    if (this.addReview && username) {
      this.addReview.description = description;
      this.addReview.grade = grade;
      const idTechnicianProfile = await this.technicianService.getTechnicianProfileByUsername(username);
      if(idTechnicianProfile!== undefined)
        this.addReview.technicianId = idTechnicianProfile;
      const idUser = await this.userProfile.getUserIdProfileByUserName(localStorage.getItem('username'));
      if(idUser)
        this.addReview.userId=idUser;
      this.reviewServide.addReview(this.addReview).subscribe(
        ()=> {this.loadTechnicians();
        this.addReview.description= "";
        this.addReview.grade = 0;
        this.notification.showNotification("Review added with success");
    });
    }
  }
}
