import React from 'react';
import NavLink from './NavLink'

class Main extends React.Component {
	render() {
		return (
		<div>
			<ul role="nav">
			  <li><NavLink to="/about">About</NavLink></li>
			  <li><NavLink to="/table">Table</NavLink></li>
			</ul>
			{this.props.children}
		</div>
		)
	}
}

export default Main;