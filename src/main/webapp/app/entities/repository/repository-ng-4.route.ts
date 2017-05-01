import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RepositoryNg4Component } from './repository-ng-4.component';
import { RepositoryNg4DetailComponent } from './repository-ng-4-detail.component';
import { RepositoryNg4PopupComponent } from './repository-ng-4-dialog.component';
import { RepositoryNg4DeletePopupComponent } from './repository-ng-4-delete-dialog.component';

import { Principal } from '../../shared';

export const repositoryRoute: Routes = [
  {
    path: 'repository-ng-4',
    component: RepositoryNg4Component,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'tmsApp.repository.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'repository-ng-4/:id',
    component: RepositoryNg4DetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'tmsApp.repository.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const repositoryPopupRoute: Routes = [
  {
    path: 'repository-ng-4-new',
    component: RepositoryNg4PopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'tmsApp.repository.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'repository-ng-4/:id/edit',
    component: RepositoryNg4PopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'tmsApp.repository.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'repository-ng-4/:id/delete',
    component: RepositoryNg4DeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'tmsApp.repository.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
