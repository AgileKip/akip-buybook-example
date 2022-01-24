import { IBuyBook } from '@/shared/model/buy-book.model';

export interface IBuyBookBinding {
  id?: number;
  processInstance?: any | null;
  buyBook?: IBuyBook | null;
}

export class BuyBookBinding implements IBuyBookBinding {
  constructor(public id?: number, public processInstance?: any | null, public buyBook?: IBuyBook | null) {}
}
