/*
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
/**
 * resize.js 0.3 970811
 * by gary smith
 * js component for "reloading page onResize"
 */

if (document.layers) {
    window.onresize = resize;
    window.saveInnerWidth = window.innerWidth;
    window.saveInnerHeight = window.innerHeight;
}

function resize() {
if (document.layers) {
    if (saveInnerWidth < window.innerWidth || 
        saveInnerWidth > window.innerWidth || 
        saveInnerHeight > window.innerHeight || 
        saveInnerHeight < window.innerHeight ) 
    {
        window.location.reload();
    }
 }
}
