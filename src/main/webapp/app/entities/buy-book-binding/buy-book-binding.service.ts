import axios from 'axios';

import { IBuyBookBinding } from '@/shared/model/buy-book-binding.model';

const baseApiUrl = 'api/buy-book-bindings';

export default class BuyBookBindingService {
  public find(id: number): Promise<IBuyBookBinding> {
    return new Promise<IBuyBookBinding>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IBuyBookBinding): Promise<IBuyBookBinding> {
    return new Promise<IBuyBookBinding>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
