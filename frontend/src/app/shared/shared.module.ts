import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DragDropModule } from '@angular/cdk/drag-drop';

// Angular Material
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { MatBadgeModule } from '@angular/material/badge';
import { MatMenuModule } from '@angular/material/menu';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatDividerModule } from '@angular/material/divider';

import { NavbarComponent } from './components/navbar/navbar.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { LayoutComponent } from './components/layout/layout.component';
import { ToastComponent } from './components/toast/toast.component';
import { StatusColorPipe } from './pipes/status-color.pipe';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';

const MATERIAL = [
  MatToolbarModule, MatSidenavModule, MatListModule, MatIconModule,
  MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule,
  MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatDialogModule,
  MatSnackBarModule, MatProgressSpinnerModule, MatChipsModule, MatBadgeModule,
  MatMenuModule, MatTooltipModule, MatTableModule, MatTabsModule,
  MatProgressBarModule, MatDividerModule
];

@NgModule({
  declarations: [
    NavbarComponent, SidebarComponent,
    LayoutComponent, ToastComponent, StatusColorPipe
  ],
  imports: [CommonModule, RouterModule, FormsModule, ReactiveFormsModule, 
    DragDropModule, ...MATERIAL],
  exports: [
    CommonModule, RouterModule, FormsModule, ReactiveFormsModule,
    DragDropModule, NavbarComponent, SidebarComponent,
    LayoutComponent, ToastComponent, StatusColorPipe, ...MATERIAL
  ]
})
export class SharedModule {}