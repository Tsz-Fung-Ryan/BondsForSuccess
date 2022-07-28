import { Injectable } from '@angular/core';
import { FileSystemFileEntry, NgxFileDropEntry } from 'ngx-file-drop';
import { DefaultService, Match } from '../libs/api/generated-code/api';
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TablesService {
  constructor(
    private defaultService: DefaultService,
  ) {}

  async uploadFiles(menteeFile: NgxFileDropEntry, mentorFile: NgxFileDropEntry): Promise<Match[]> {
    const menteeFileSystemEntry = menteeFile.fileEntry;
    const mentorFileSystemEntry = mentorFile.fileEntry;

    if (menteeFileSystemEntry.isFile && mentorFileSystemEntry.isFile) {
      const menteeFileSystemFileEntry = menteeFileSystemEntry as FileSystemFileEntry;
      const mentorFileSystemFileEntry = mentorFileSystemEntry as FileSystemFileEntry;
      const [ menteeBlobFile, mentorBlobFile ] = await Promise.all([this.getFileFromFileSystemFileEntry(menteeFileSystemFileEntry), this.getFileFromFileSystemFileEntry(mentorFileSystemFileEntry)]);

      return lastValueFrom(this.defaultService.createTablePost(menteeBlobFile, mentorBlobFile));
    }
    throw new Error('One of the passed in dropped files is not a file');
  }

  async getFileFromFileSystemFileEntry(fileSystemFileEntry: FileSystemFileEntry): Promise<File> {
    return new Promise((resolve, _reject) => {
      fileSystemFileEntry.file((file: File) => {
        resolve(file);
      });
    });
  }
}
