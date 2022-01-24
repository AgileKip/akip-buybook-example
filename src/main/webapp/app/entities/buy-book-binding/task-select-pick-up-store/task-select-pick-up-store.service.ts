import axios from 'axios';
import { TaskSelectPickUpStoreContext } from './task-select-pick-up-store.model';

const baseApiUrl = 'api/buy-book-binding/task-select-pick-up-store';

export default class TaskSelectPickUpStoreService {
  public loadContext(taskId: number): Promise<TaskSelectPickUpStoreContext> {
    return new Promise<TaskSelectPickUpStoreContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskSelectPickUpStoreContext> {
    return new Promise<TaskSelectPickUpStoreContext>((resolve, reject) => {
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

  public complete(taskSelectPickUpStoreContext: TaskSelectPickUpStoreContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskSelectPickUpStoreContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
