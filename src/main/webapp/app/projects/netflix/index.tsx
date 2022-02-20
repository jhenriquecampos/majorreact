import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import React from 'react';
import { Switch } from 'react-router-dom';
import Netflix from './netflix';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute path={match.url} component={Netflix} />
    </Switch>
  </>
);

export default Routes;
