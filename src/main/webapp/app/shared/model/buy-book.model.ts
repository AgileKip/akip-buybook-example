import { IBook } from '@/shared/model/book.model';
import { IStore } from '@/shared/model/store.model';

export interface IBuyBook {
  id?: number;
  purpose?: string;
  description?: string | null;
  date?: Date | null;
  deliveryHome?: boolean | null;
  deliveryAddress?: string | null;
  paymentDetails?: string | null;
  purchaseReview?: string | null;
  book?: IBook | null;
  store?: IStore | null;
}

export class BuyBook implements IBuyBook {
  constructor(
    public id?: number,
    public purpose?: string,
    public description?: string | null,
    public date?: Date | null,
    public deliveryHome?: boolean | null,
    public deliveryAddress?: string | null,
    public paymentDetails?: string | null,
    public purchaseReview?: string | null,
    public book?: IBook | null,
    public store?: IStore | null
  ) {
    this.deliveryHome = this.deliveryHome ?? false;
  }
}
