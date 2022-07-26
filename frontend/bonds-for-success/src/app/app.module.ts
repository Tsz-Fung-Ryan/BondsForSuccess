import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { MatGridListModule } from '@angular/material/grid-list';
import { MatTableModule } from '@angular/material/table';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { NgxFileDropModule } from 'ngx-file-drop';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FileInputComponent } from './file-input/file-input.component';
import { MenteeMentorTableComponent } from './mentee-mentor-table/mentee-mentor-table.component';

import { TablesService } from './services/tables.service';

@NgModule({
  declarations: [
    AppComponent,
    FileInputComponent,
    MenteeMentorTableComponent,
  ],
  imports: [
    AppRoutingModule,
    BrowserAnimationsModule,
    BrowserModule,
    DragDropModule,
    MatGridListModule,
    MatTableModule,
    NgxFileDropModule,
  ],
  providers: [
    TablesService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
