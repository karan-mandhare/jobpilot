import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { NgChartsModule } from 'ng2-charts';
import { AnalyticsComponent } from './analytics/analytics.component';

const routes: Routes = [{ path: '', component: AnalyticsComponent }];

@NgModule({
  declarations: [AnalyticsComponent],
  imports: [SharedModule, NgChartsModule, RouterModule.forChild(routes)]
})
export class AnalyticsModule {}