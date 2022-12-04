import { NgModule } from '@angular/core';
import { AuthModule } from 'angular-auth-oidc-client';


@NgModule({
    imports: [AuthModule.forRoot({
        config: {
            authority: 'acho.bg',
            redirectUrl: window.location.origin,
            clientId: 'please-enter-auth0-clientId',
            scope: 'openid profile offline_access email',
            responseType: 'code',
            silentRenew: true,
            useRefreshToken: true,
            secureRoutes: ['http://localhost:8080/'],
            customParamsAuthRequest: {
                audience: 'http://localhost:8080/'
            }
        }
    })],
    exports: [AuthModule],
})
export class AuthConfigModule { }
