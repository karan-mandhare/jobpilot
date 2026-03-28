import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { AiPrepComponent } from './ai-prep/ai-prep.component';

const routes: Routes = [{ path: '', component: AiPrepComponent }];

@NgModule({
  declarations: [AiPrepComponent],
  imports: [SharedModule, RouterModule.forChild(routes)]
})
export class AiPrepModule {}