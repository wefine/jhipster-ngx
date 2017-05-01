import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TmsSharedModule } from '../../shared';
import {
    RepositoryNg4Service,
    RepositoryNg4PopupService,
    RepositoryNg4Component,
    RepositoryNg4DetailComponent,
    RepositoryNg4DialogComponent,
    RepositoryNg4PopupComponent,
    RepositoryNg4DeletePopupComponent,
    RepositoryNg4DeleteDialogComponent,
    repositoryRoute,
    repositoryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...repositoryRoute,
    ...repositoryPopupRoute,
];

@NgModule({
    imports: [
        TmsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RepositoryNg4Component,
        RepositoryNg4DetailComponent,
        RepositoryNg4DialogComponent,
        RepositoryNg4DeleteDialogComponent,
        RepositoryNg4PopupComponent,
        RepositoryNg4DeletePopupComponent,
    ],
    entryComponents: [
        RepositoryNg4Component,
        RepositoryNg4DialogComponent,
        RepositoryNg4PopupComponent,
        RepositoryNg4DeleteDialogComponent,
        RepositoryNg4DeletePopupComponent,
    ],
    providers: [
        RepositoryNg4Service,
        RepositoryNg4PopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TmsRepositoryNg4Module {}
