package com.csselect.API.httpAPI;
import com.csselect.API.APIFacadePlayer;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * only request from players.
 */
public class Games extends Servlet {



    public void get(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        if (!isPlayer()) {
            throw new HttpError(HttpServletResponse.SC_FORBIDDEN);
        }
        /*
         this method only parses the request string and redirects the call
         */
        String requestString = req.getPathInfo();
        if (requestString == null) {
            getGames(req, resp);
        } else if (requestString.matches("/[0-9]+")) {
            getGame(req, resp);
        }


    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {

        if (!isPlayer()) {
            throw new HttpError(HttpServletResponse.SC_FORBIDDEN);
        }
        String requestString = req.getPathInfo();
        if (requestString.matches("/[0-9]+/accept")) {
            acceptInvite(req, resp);
        } else if (requestString.matches("/[0-9]+/decline")) {
            declineInvite(req, resp);
        } else if (requestString.matches("/[0-9]+/play")) {
            playRound(req, resp);
        } else if (requestString.matches("/[0-9]+/skip")) {
            skipRound(req, resp);
        } else if (requestString.matches("/[0-9]+/start")) {
            startRound(req, resp);
        }
    }


    private void skipRound(HttpServletRequest req, HttpServletResponse resp) throws HttpError {

        String uselessString = getParameter("useless", req);
        Collection<Integer> useless = new Gson().fromJson(uselessString, Collection.class);
        getPlayerFacade().skipRound(useless);
    }

    private void playRound(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {

        String selectedString = getParameter("selected", req);
        String uselessString = getParameter("useless", req);
        Collection<Integer> selected = new Gson().fromJson(selectedString, Collection.class);
        Collection<Integer> useless = new Gson().fromJson(uselessString, Collection.class);
        returnAsJson(resp, getPlayerFacade().selectFeatures(selected, useless));
    }

    private void declineInvite(HttpServletRequest req, HttpServletResponse resp) throws HttpError {

        getPlayerFacade().declineInvite(getId(req.getPathInfo()));
    }

    private void acceptInvite(HttpServletRequest req, HttpServletResponse resp) throws HttpError {

        getPlayerFacade().acceptInvite(getId(req.getPathInfo()));
    }

    private void startRound(HttpServletRequest req, HttpServletResponse resp) throws IOException, HttpError {

        int gameId = getId(req.getPathInfo());
        returnAsJson(resp, getPlayerFacade().startRound(gameId));
    }



    private void getGames(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {

        returnAsJson(resp, getPlayerFacade().getGames());
    }



    public void getGame(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {

        APIFacadePlayer facade = getPlayerFacade();
        // getting the game id
        int gameId = Integer.parseInt(req.getPathInfo().substring(1));
        returnAsJson(resp, facade.getGame(gameId));
    }

    private int getId(String urlPath) {
        Pattern p = Pattern.compile("/([0-9]+)*");
        Matcher m = p.matcher(urlPath);
        m.find();
        return Integer.parseInt(m.group(1));
    }

}
