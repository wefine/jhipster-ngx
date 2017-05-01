import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from "@angular/core";

import { TmsRepositoryNg4Module } from './repository/repository-ng-4.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TmsRepositoryNg4Module,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TmsEntityModule {
}
