import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

  constructor(
    private router: Router
  ) {
    //this.currentUser = userService.currentUser();
  }

    // When click logout button Then remove user from localStorage and navigate to homepage
  onUserLogout() {
    localStorage.removeItem('user');
    this.router.navigate(['/']); // Corrected navigation path
  }

}
