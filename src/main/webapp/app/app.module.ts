import "./vendor.ts";

import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { Ng2Webstorage } from "ng2-webstorage";

import { TmsSharedModule, UserRouteAccessService } from "./shared";
import { TmsHomeModule } from "./home/home.module";
import { TmsAdminModule } from "./admin/admin.module";
import { TmsAccountModule } from "./account/account.module";
import { TmsEntityModule } from "./entities/entity.module";

import {
    ActiveMenuDirective,
    ErrorComponent,
    FooterComponent,
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    PageRibbonComponent,
    ProfileService
} from "./layouts";
import { customHttpProvider } from "./blocks/interceptor/http.provider";
import { PaginationConfig } from "./blocks/config/uib-pagination.config";

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({prefix: 'jhi', separator: '-'}),
        TmsSharedModule,
        TmsHomeModule,
        TmsAdminModule,
        TmsAccountModule,
        TmsEntityModule
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [JhiMainComponent]
})
export class TmsAppModule {
}
