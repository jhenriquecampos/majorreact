import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import React from 'react';
import Netflix from './netflix';

const Routes = ({ match }) => (
  <div>
    <ErrorBoundaryRoute path={`${match.url}/netflix`} component={Netflix} />
  </div>
);

export default Routes;
