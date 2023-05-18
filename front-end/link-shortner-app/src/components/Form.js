import React, { useState, useEffect } from 'react'
import { fetchLink, insertLink, updateLink } from '.././api'
import { Redirector } from './Redirector';
import { Status } from './Status';

export const Form = () => {
    // const [url, setUrl] = useState({id: '', expiryTimeStamp: '', rawLink: '', shortLink: '', status: ''});
    const [rawLink, setRawLink] = useState("");
    const [status, setStatus] = useState("Generate Link");
    const [shortLinkAct, setShortLinkAct] = useState("");

    const getCurrOrigin = () => window.location.origin

    const handleSubmitLink = (e) =>{
        e.preventDefault();
    
        fetchLink(rawLink).then(fetch =>{
          if(!fetch.data){
            insertLink(rawLink).then(insert =>{
                console.log('inserted data')
                setStatus('Created new Short Link')
                
                setShortLinkAct(getCurrOrigin+insert.data.shortLink);
                setRawLink(insert.data.rawLink);
            })
          }else{
            const fetchStatus = fetch.data.status;
            if(fetchStatus === 'Fresh Link generated'){
              setStatus("Link Present Already");
              
              setShortLinkAct(getCurrOrigin+fetch.data.shortLink);
              setRawLink(fetch.data.rawLink);
            }else if(fetchStatus === 'Link has expired'){
              setStatus("Link Expired, Click on Update to genereate new Short Link");
              
            }
          }
        }).catch(ferr =>{
          console.log(ferr)
        })
    
      }
    
      const handleUpdate = (e) =>{

        fetchLink(rawLink).then(fetch =>{
            if(fetch.data){
                setStatus('Short Link Updated');
                updateLink(rawLink).then(updateResp =>{
                    
                    console.log(updateResp.data);
                    setShortLinkAct(getCurrOrigin+updateResp.data.shortLink);
                    setRawLink(updateResp.data.rawLink);
                })
                .catch(err =>{
                    console.log('handleUpdate',err);
                });
            }else{
                // handling the case where the link does not exist but user tries to update it
                setStatus('Click on Generate to generate link')
            }
        })
        .catch(ferr =>{
            console.log(ferr);
        });
        
      }

  return (
    <div>
        <form onSubmit={handleSubmitLink} >
        <label>
          Raw Link:
          <input type="text" value={rawLink}
           onChange={(e) => setRawLink(e.target.value)} />
        </label>

        <button type='submit'>Generate</button>
        <button type='button' onClick={(e) => handleUpdate(e)}>Update</button>
      </form>
      <Redirector rawLink={rawLink} shortLink={shortLinkAct}/>
      <Status status={status} />
    </div>
  )
}
