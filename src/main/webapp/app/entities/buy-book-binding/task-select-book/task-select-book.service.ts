import axios from 'axios';
import { TaskSelectBookContext } from './task-select-book.model';

const baseApiUrl = 'api/buy-book-binding/task-select-book';

export default class TaskSelectBookService {
  public loadContext(taskId: number): Promise<TaskSelectBookContext> {
    return new Promise<TaskSelectBookContext>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${taskId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public claim(taskId: number): Promise<TaskSelectBookContext> {
    return new Promise<TaskSelectBookContext>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${taskId}/claim`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public complete(taskSelectBookContext: TaskSelectBookContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskSelectBookContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
