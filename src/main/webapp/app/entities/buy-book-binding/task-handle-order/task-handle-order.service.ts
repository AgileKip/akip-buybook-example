import axios from 'axios';
import { TaskHandleOrderContext } from './task-handle-order.model';

const baseApiUrl = 'api/buy-book-binding/task-handle-order';

export default class TaskHandleOrderService {
  public loadContext(taskId: number): Promise<TaskHandleOrderContext> {
    return new Promise<TaskHandleOrderContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskHandleOrderContext> {
    return new Promise<TaskHandleOrderContext>((resolve, reject) => {
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

  public complete(taskHandleOrderContext: TaskHandleOrderContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskHandleOrderContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
