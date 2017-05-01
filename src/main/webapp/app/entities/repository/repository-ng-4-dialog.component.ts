import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { RepositoryNg4 } from './repository-ng-4.model';
import { RepositoryNg4PopupService } from './repository-ng-4-popup.service';
import { RepositoryNg4Service } from './repository-ng-4.service';

@Component({
    selector: 'jhi-repository-ng-4-dialog',
    templateUrl: './repository-ng-4-dialog.component.html'
})
export class RepositoryNg4DialogComponent implements OnInit {

    repository: RepositoryNg4;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private repositoryService: RepositoryNg4Service,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['repository']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.repository.id !== undefined) {
            this.repositoryService.update(this.repository)
                .subscribe((res: RepositoryNg4) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.repositoryService.create(this.repository)
                .subscribe((res: RepositoryNg4) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: RepositoryNg4) {
        this.eventManager.broadcast({ name: 'repositoryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-repository-ng-4-popup',
    template: ''
})
export class RepositoryNg4PopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private repositoryPopupService: RepositoryNg4PopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.repositoryPopupService
                    .open(RepositoryNg4DialogComponent, params['id']);
            } else {
                this.modalRef = this.repositoryPopupService
                    .open(RepositoryNg4DialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
