import axios from 'axios';

export const url = `http://localhost:8071/link-short/`;

export const fetchLink = async (rawLinkReq) => {

	let myUrl = url + `raw-link`;

	return axios.get(myUrl, { params: { link: rawLinkReq } }, { headers: { 'Content-Type': 'application/json' } });

}
export const insertLink = (rawLink) => {

	return axios.post(url + 'insert', { "link": rawLink }, { headers: { 'Content-Type': 'application/json' } });

}

export const updateLink = (rawLink) => {
	// let newShortLink = 'blank'; 
	return axios.put(url + 'update', { "link": rawLink }, { headers: { 'Content-Type': 'application/json' } });

}

export const fetchByShortLink = (shortLinkParam) => {
	let myUrl = url + `short-link`;

	return axios.get(myUrl, { params: { link: shortLinkParam }, headers: { 'Content-type': 'application/json' } });
}