import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { RepositoryNg4 } from './repository-ng-4.model';
import { RepositoryNg4Service } from './repository-ng-4.service';

@Component({
    selector: 'jhi-repository-ng-4-detail',
    templateUrl: './repository-ng-4-detail.component.html'
})
export class RepositoryNg4DetailComponent implements OnInit, OnDestroy {

    repository: RepositoryNg4;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private repositoryService: RepositoryNg4Service,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['repository']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRepositories();
    }

    load(id) {
        this.repositoryService.find(id).subscribe((repository) => {
            this.repository = repository;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRepositories() {
        this.eventSubscriber = this.eventManager.subscribe('repositoryListModification', (response) => this.load(this.repository.id));
    }
}
