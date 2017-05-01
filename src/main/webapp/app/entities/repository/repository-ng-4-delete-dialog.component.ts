import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { RepositoryNg4 } from './repository-ng-4.model';
import { RepositoryNg4PopupService } from './repository-ng-4-popup.service';
import { RepositoryNg4Service } from './repository-ng-4.service';

@Component({
    selector: 'jhi-repository-ng-4-delete-dialog',
    templateUrl: './repository-ng-4-delete-dialog.component.html'
})
export class RepositoryNg4DeleteDialogComponent {

    repository: RepositoryNg4;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private repositoryService: RepositoryNg4Service,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['repository']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.repositoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'repositoryListModification',
                content: 'Deleted an repository'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-repository-ng-4-delete-popup',
    template: ''
})
export class RepositoryNg4DeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private repositoryPopupService: RepositoryNg4PopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.repositoryPopupService
                .open(RepositoryNg4DeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
