package com.growbiz.backend.FreeSlot.models;

import com.growbiz.backend.Responses.Basic.BasicResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class FreeSlotsResponse extends BasicResponse {
    Map<Date, List<SlotRange>> freeSlots;
}
