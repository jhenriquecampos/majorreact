import { IUser } from 'app/shared/model/user.model';

export interface INetflixMyList {
  id?: number;
  movieCod?: number;
  user?: IUser;
}

export const defaultValue: Readonly<INetflixMyList> = {};
