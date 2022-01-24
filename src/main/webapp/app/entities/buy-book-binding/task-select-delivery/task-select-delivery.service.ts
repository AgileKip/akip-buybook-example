import axios from 'axios';
import { TaskSelectDeliveryContext } from './task-select-delivery.model';

const baseApiUrl = 'api/buy-book-binding/task-select-delivery';

export default class TaskSelectDeliveryService {
  public loadContext(taskId: number): Promise<TaskSelectDeliveryContext> {
    return new Promise<TaskSelectDeliveryContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskSelectDeliveryContext> {
    return new Promise<TaskSelectDeliveryContext>((resolve, reject) => {
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

  public complete(taskSelectDeliveryContext: TaskSelectDeliveryContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskSelectDeliveryContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
