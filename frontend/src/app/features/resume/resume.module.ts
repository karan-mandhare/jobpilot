import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { ResumeComponent } from './resume/resume.component';

const routes: Routes = [{ path: '', component: ResumeComponent }];

@NgModule({
  declarations: [ResumeComponent],
  imports: [SharedModule, RouterModule.forChild(routes)]
})
export class ResumeModule {}