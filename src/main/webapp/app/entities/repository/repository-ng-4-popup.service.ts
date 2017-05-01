import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RepositoryNg4 } from './repository-ng-4.model';
import { RepositoryNg4Service } from './repository-ng-4.service';
@Injectable()
export class RepositoryNg4PopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private repositoryService: RepositoryNg4Service

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.repositoryService.find(id).subscribe((repository) => {
                repository.checkAt = this.datePipe
                    .transform(repository.checkAt, 'yyyy-MM-ddThh:mm');
                this.repositoryModalRef(component, repository);
            });
        } else {
            return this.repositoryModalRef(component, new RepositoryNg4());
        }
    }

    repositoryModalRef(component: Component, repository: RepositoryNg4): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.repository = repository;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
