import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { TmsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RepositoryNg4DetailComponent } from '../../../../../../main/webapp/app/entities/repository/repository-ng-4-detail.component';
import { RepositoryNg4Service } from '../../../../../../main/webapp/app/entities/repository/repository-ng-4.service';
import { RepositoryNg4 } from '../../../../../../main/webapp/app/entities/repository/repository-ng-4.model';

describe('Component Tests', () => {

    describe('RepositoryNg4 Management Detail Component', () => {
        let comp: RepositoryNg4DetailComponent;
        let fixture: ComponentFixture<RepositoryNg4DetailComponent>;
        let service: RepositoryNg4Service;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TmsTestModule],
                declarations: [RepositoryNg4DetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RepositoryNg4Service,
                    EventManager
                ]
            }).overrideComponent(RepositoryNg4DetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RepositoryNg4DetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RepositoryNg4Service);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RepositoryNg4(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.repository).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
