import { Component, OnInit } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isAuthenticated: boolean = false;

  constructor(private securityService: OidcSecurityService) { }

  ngOnInit(): void {
    this.securityService.isAuthenticated$
      .subscribe(({ isAuthenticated }) => {
        this.isAuthenticated = isAuthenticated;
      });
  }

  login() {
    this.securityService.authorize();
  }

  logOut() {
    this.securityService.logoffAndRevokeTokens();
  }
}
