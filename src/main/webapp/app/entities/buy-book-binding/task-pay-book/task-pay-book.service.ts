import axios from 'axios';
import { TaskPayBookContext } from './task-pay-book.model';

const baseApiUrl = 'api/buy-book-binding/task-pay-book';

export default class TaskPayBookService {
  public loadContext(taskId: number): Promise<TaskPayBookContext> {
    return new Promise<TaskPayBookContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskPayBookContext> {
    return new Promise<TaskPayBookContext>((resolve, reject) => {
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

  public complete(taskPayBookContext: TaskPayBookContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskPayBookContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
