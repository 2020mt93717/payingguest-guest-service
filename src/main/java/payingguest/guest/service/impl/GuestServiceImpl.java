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

package payingguest.guest.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import payingguest.guest.domain.Guest;
import payingguest.guest.repository.GuestRepository;
import payingguest.guest.service.GuestService;

@Service
public class GuestServiceImpl implements GuestService {

    @Autowired
    private GuestRepository mGuestRepository;

    @Override
    public Iterable<Guest> loadAllGuests() {
        return mGuestRepository.findAll();
    }

    @Transactional
    @Override
    public void createGuest(Guest pGuest) {
        mGuestRepository.save(pGuest);
    }

    @Transactional
    @Override
    public void updateGuest(Guest pGuest) {
        mGuestRepository.save(pGuest);
    }

    @Transactional
    @Override
    public void deleteGuest(Long pGuestId) {
        mGuestRepository.deleteById(pGuestId);
    }

    @Override
    public Optional<Guest> findGuestByGuestId(Long pGuestId) {
        return mGuestRepository.findById(pGuestId);
    }

    @Override
    public Optional<Guest> findGuestByMobileNumber(String pMobileNumber) {
        return mGuestRepository.findByMobileNumber(pMobileNumber);
    }
}
