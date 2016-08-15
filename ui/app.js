import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Route, hashHistory, IndexRoute } from 'react-router'
import Main from './modules/Main.js';
import About from './modules/About.js';
import ContentTable from './modules/ContentTable.js';
import NotFoundRoute from './modules/404.js';

ReactDOM.render((
  <Router history={hashHistory}>
    <Route path="/" component={Main}>
		<Route path="/about" component={About}>
			<Route path="/about/:paramAbout" component={About}/>
		</Route>
		<Route path="/table" component={ContentTable}/>
	</Route>
	<Route path="*" component={NotFoundRoute} />
  </Router>
), document.getElementById('app'))
