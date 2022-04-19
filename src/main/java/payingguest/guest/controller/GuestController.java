/***************************************************************************************
 * MIT License
 *
 * Copyright (c) 2022 2020mt93717
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * **************************************************************************************/
package payingguest.guest.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import payingguest.guest.domain.Guest;
import payingguest.guest.service.GuestService;

@RestController
public class GuestController {

    @Autowired
    private GuestService mGuestService;

    @Autowired
    private KafkaTemplate<String, String> mKafkaTemplate;

    private static final String TOPIC = "NewTopic";

    @GetMapping("/guest")
    public Iterable<Guest> loadAllGuests() {
        return mGuestService.loadAllGuests();
    }

    @GetMapping("/guest/{GuestId}")
    public Guest findGuestById(final @PathVariable("GuestId") Long pGuestId) {
        Optional<Guest> myGuest = mGuestService.findGuestByGuestId(pGuestId);
        return myGuest.isPresent() ? myGuest.get() : null;
    }

    @GetMapping("/guest/mobile/{MobileNumber}")
    public Guest findGuestByMobileNumber(@PathVariable("MobileNumber") String pMobileNumber) {
        Optional<Guest> myGuest = mGuestService.findGuestByMobileNumber(pMobileNumber);
        return myGuest.isPresent() ? myGuest.get() : null;
    }

    @PostMapping(
            value = "/guest",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Guest> createGuest(@RequestBody Guest pGuest) {
        if(pGuest.getGuestId() == 0) {
            mGuestService.createGuest(pGuest);
        } else {
            mGuestService.updateGuest(pGuest);
        }
        return new ResponseEntity<>(pGuest, HttpStatus.OK);
    }

    @DeleteMapping("/guest/{GuestId}")
    public ResponseEntity<String> deleteGuest(@PathVariable("GuestId") long pGuestId) {
        mKafkaTemplate.send(TOPIC, "delete Guest => " + pGuestId);
        return new ResponseEntity<>("Published Successfully", HttpStatus.OK);
    }
}
