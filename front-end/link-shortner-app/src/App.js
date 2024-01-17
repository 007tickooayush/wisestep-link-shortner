import './App.css'
import React, { useEffect } from 'react'
import { Form } from './components/Form';
import { fetchByShortLink, url } from './api';
import { Route, Routes, useLocation } from 'react-router-dom';
import NotFound from './components/NotFound';

function App() {

	const location = useLocation();

	useEffect(() => {
		const path = location.pathname.slice(1);
		// window.open(`${url}${path}`);
		if(path !== ''){
			fetchByShortLink(path).then((val) => {
				
				window.open(`${url}${path}`);
	
				console.log('fetchByShortLink::val=',val);
				console.log('`${url}${path}`', `${url}${path}`)
			}).catch(err => {
				console.error('fetchByShortLink::err=',err);
			})
			console.log('useLocation:',location.pathname.slice(1));
		}
	},[location]);

	return (
		<Routes>
			{/* <a href={rawLink? 'https://'+rawLink:'blank'}> */}
			{/* <Route path="/:shortLink" element={<Redirector />} /> */}
			<Route path="/" element={<Form />} />
			<Route path='*' element={<NotFound />}/>
		</Routes>
	)
	// return (
	//   <div className="App">
	//     <header className="App-header">
	//       <img src={logo} className="App-logo" alt="logo" />
	//       <p>
	//         Edit <code>src/App.js</code> and save to reload.
	//       </p>
	//       {/* <a
	//         className="App-link"
	//         href="https://reactjs.org"
	//         target="_blank"
	//         rel="noopener noreferrer"
	//       >
	//         Learn React
	//       </a> */}
	//     </header>
	//   </div>
	// );
}

export default App
