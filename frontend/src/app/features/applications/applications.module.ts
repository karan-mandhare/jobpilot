import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { KanbanBoardComponent } from './kanban-board/kanban-board.component';
import { ApplicationFormComponent } from './application-form/application-form.component';
import { ApplicationDetailComponent } from './application-detail/application-detail.component';

const routes: Routes = [{ path: '', component: KanbanBoardComponent }];

@NgModule({
  declarations: [KanbanBoardComponent, ApplicationFormComponent, ApplicationDetailComponent],
  imports: [SharedModule, RouterModule.forChild(routes)]
})
export class ApplicationsModule {}