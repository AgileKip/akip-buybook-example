import axios from 'axios';
import { TaskAddShippingInfoContext } from './task-add-shipping-info.model';

const baseApiUrl = 'api/buy-book-binding/task-add-shipping-info';

export default class TaskAddShippingInfoService {
  public loadContext(taskId: number): Promise<TaskAddShippingInfoContext> {
    return new Promise<TaskAddShippingInfoContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskAddShippingInfoContext> {
    return new Promise<TaskAddShippingInfoContext>((resolve, reject) => {
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

  public complete(taskAddShippingInfoContext: TaskAddShippingInfoContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskAddShippingInfoContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
