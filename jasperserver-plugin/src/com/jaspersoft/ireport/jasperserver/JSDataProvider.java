/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.InputControlQueryDataRow;
import java.util.List;
import java.util.Map;

/**
 * This is a simple interface to get data from a generic server
 * query.
 * This can be extended in the future.
 * @author gtoffoli
 */
public interface JSDataProvider {

    /**
     *
     * @param server
     * @param dsName
     * @param query
     * @param parameters
     * @return a list of InputControlQueryDataRow
     */
    public List<InputControlQueryDataRow> getData(JServer server, String dsName, String query, Map parameters);

    /**
     * Returns true if the specified language is supported.
     * 
     * @param language
     * @return
     */
    public boolean supports(String language);


}
