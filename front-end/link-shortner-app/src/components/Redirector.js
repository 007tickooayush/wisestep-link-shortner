import React from 'react'
import {Link} from 'react-router-dom';

export const Redirector = ({rawLink,shortLink}) => {
  return (
      <div>
        <p>NOTE: Enter url in format of 'www.domain.com' </p>
        {(rawLink && shortLink)?
            // <Link to={rawLink} target="_blank">
            //     {shortLink}
            // </Link>
            
            <a href={'https://'+rawLink}>{shortLink}</a>
            :
            null
        }
      </div>
  )
}
