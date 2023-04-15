import React from 'react';
import '../../App.css';
import Footer from '../footer';
import { Button } from '../button';

export default function StartGraphing(){
    return (
        <div className='upload-container'>
        <h1 className='startgraphing'> START GRAPHING </h1>
            <section className="upload-mf4">
                <p className="upload-mf4-heading">
                    Upload MF4 and DBF files here
                </p>
                <div classname='input-areas'>
                    <form>
                        <input
                            type='mf4'
                            name='mf4'
                            placeholder='file.mf4'
                        />
                        <br/>
                        <input
                            type='dbf'
                            name='dbf'
                            placeholder='file.dbf'
                        />
                        <Button buttonStyle='btn--primary'>Submit</Button>
                    </form>
                </div>
            </section>
            <Footer />
        </div>


        );



}