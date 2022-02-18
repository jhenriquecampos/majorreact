import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NetflixMyList from './netflix-my-list';
import NetflixMyListDetail from './netflix-my-list-detail';
import NetflixMyListUpdate from './netflix-my-list-update';
import NetflixMyListDeleteDialog from './netflix-my-list-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NetflixMyListUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NetflixMyListUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NetflixMyListDetail} />
      <ErrorBoundaryRoute path={match.url} component={NetflixMyList} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={NetflixMyListDeleteDialog} />
  </>
);

export default Routes;
