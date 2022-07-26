import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';

import { MatGridListModule } from '@angular/material/grid-list';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { NgxFileDropModule } from 'ngx-file-drop';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FileInputComponent } from './file-input/file-input.component';
import { MenteeMentorTableComponent } from './mentee-mentor-table/mentee-mentor-table.component';

import { TablesService } from './services/tables.service';

import { ApiModule } from './libs/api/generated-code/api';

@NgModule({
  declarations: [
    AppComponent,
    FileInputComponent,
    MenteeMentorTableComponent,
  ],
  imports: [
    ApiModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    BrowserModule,
    DragDropModule,
    HttpClientModule,
    MatGridListModule,
    MatSnackBarModule,
    MatTableModule,
    MatTabsModule,
    NgxFileDropModule,
  ],
  providers: [
    TablesService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
