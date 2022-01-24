import { IUser } from '@/shared/model/user.model';
import { IPublisher } from '@/shared/model/publisher.model';

export interface IBook {
  id?: number;
  name?: string;
  description?: string | null;
  pubYear?: number;
  coverContentType?: string | null;
  cover?: string | null;
  author?: IUser | null;
  publisher?: IPublisher | null;
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public pubYear?: number,
    public coverContentType?: string | null,
    public cover?: string | null,
    public author?: IUser | null,
    public publisher?: IPublisher | null
  ) {}
}
